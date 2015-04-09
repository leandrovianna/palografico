package com.leandro.palografico.imageproc;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.leandro.palografico.Constantes;

/**
 * Created by leandro on 09/04/15.
 */
public class ImageThresholder {

    public static int average(Bitmap bitmap) {
        int threshold = 127;

        int sum = 0;

        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                sum += Color.red(bitmap.getPixel(x, y));
            }
        }

        threshold = sum / (bitmap.getHeight() * bitmap.getWidth());

        Log.i(Constantes.TAG, "threshold: " + threshold);

        return threshold;
    }

    public static int otsu(Bitmap bitmap) {
        // Calculate histogram
        int[] histData = ImageTool.calculateHistogram(bitmap);

        // Otsu's threshold algorithm
        // C++ code by Jordan Bevik <Jordan.Bevic@qtiworld.com>
        // ported to ImageJ plugin by G.Landini
        int k,kStar;  // k = the current threshold; kStar = optimal threshold
        double N1, N;    // N1 = # points with intensity <=k; N = total number of points
        double BCV, BCVmax; // The current Between Class Variance and maximum BCV
        double num, denom;  // temporary bookeeping
        double Sk;  // The total intensity for all histogram points <=k
        double S, L=256; // The total intensity of the image

        // Initialize values:
        S = N = 0;
        for (k=0; k<L; k++){
            S += (double)k * histData[k];	// Total histogram intensity
            N += histData[k];		// Total number of data points
        }

        Sk = 0;
        N1 = histData[0]; // The entry for zero intensity
        BCV = 0;
        BCVmax=0;
        kStar = 0;

        // Look at each possible threshold value,
        // calculate the between-class variance, and decide if it's a max
        for (k=1; k<L-1; k++) { // No need to check endpoints k = 0 or k = L-1
            Sk += (double)k * histData[k];
            N1 += histData[k];

            // The float casting here is to avoid compiler warning about loss of precision and
            // will prevent overflow in the case of large saturated images
            denom = (double)( N1) * (N - N1); // Maximum value of denom is (N^2)/4 =  approx. 3E10

            if (denom != 0 ){
                // Float here is to avoid loss of precision when dividing
                num = ( (double)N1 / N ) * S - Sk; 	// Maximum value of num =  255*N = approx 8E7
                BCV = (num * num) / denom;
            }
            else
                BCV = 0;

            if (BCV >= BCVmax){ // Assign the best threshold found so far
                BCVmax = BCV;
                kStar = k;
            }
        }
        // kStar += 1;	// Use QTI convention that intensity -> 1 if intensity >= k
        // (the algorithm was developed for I-> 1 if I <= k.)
        return kStar;
    }

    public static int otsu2(Bitmap bitmap) {
        int[] histData = ImageTool.calculateHistogram(bitmap);

        // Total number of pixels
        int total = bitmap.getWidth()*bitmap.getHeight();

        float sum = 0;
        for (int t=0 ; t<256 ; t++) sum += t * histData[t];

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int t=0 ; t<256 ; t++) {
            wB += histData[t];               // Weight Background
            if (wB == 0) continue;

            wF = total - wB;                 // Weight Foreground
            if (wF == 0) break;

            sumB += (float) (t * histData[t]);

            float mB = sumB / wB;            // Mean Background
            float mF = (sum - sumB) / wF;    // Mean Foreground

            // Calculate Between Class Variance
            float varBetween = (float)wB * (float)wF * (mB - mF) * (mB - mF);

            // Check if new maximum found
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = t;
            }
        }

        return threshold;
    }

    public static int huang(Bitmap bitmap) {
        int [] data = ImageTool.calculateHistogram(bitmap);

        // Implements Huang's fuzzy thresholding method
        // Uses Shannon's entropy function (one can also use Yager's entropy function)
        // Huang L.-K. and Wang M.-J.J. (1995) "Image Thresholding by Minimizing
        // the Measures of Fuzziness" Pattern Recognition, 28(1): 41-51
        // M. Emre Celebi  06.15.2007
        // Ported to ImageJ plugin by G. Landini from E Celebi's fourier_0.8 routines
        int threshold=-1;
        int ih, it;
        int first_bin;
        int last_bin;
        double sum_pix;
        double num_pix;
        double term;
        double ent;  // entropy
        double min_ent; // min entropy
        double mu_x;

		/* Determine the first non-zero bin */
        first_bin=0;
        for (ih = 0; ih < 256; ih++ ) {
            if ( data[ih] != 0 ) {
                first_bin = ih;
                break;
            }
        }

		/* Determine the last non-zero bin */
        last_bin=255;
        for (ih = 255; ih >= first_bin; ih-- ) {
            if ( data[ih] != 0 ) {
                last_bin = ih;
                break;
            }
        }
        term = 1.0 / ( double ) ( last_bin - first_bin );
        double [] mu_0 = new double[256];
        sum_pix = num_pix = 0;
        for ( ih = first_bin; ih < 256; ih++ ){
            sum_pix += (double)ih * data[ih];
            num_pix += data[ih];
			/* NUM_PIX cannot be zero ! */
            mu_0[ih] = sum_pix / num_pix;
        }

        double [] mu_1 = new double[256];
        sum_pix = num_pix = 0;
        for ( ih = last_bin; ih > 0; ih-- ){
            sum_pix += (double)ih * data[ih];
            num_pix += data[ih];
			/* NUM_PIX cannot be zero ! */
            mu_1[ih - 1] = sum_pix / ( double ) num_pix;
        }

		/* Determine the threshold that minimizes the fuzzy entropy */
        threshold = -1;
        min_ent = Double.MAX_VALUE;
        for ( it = 0; it < 256; it++ ){
            ent = 0.0;
            for ( ih = 0; ih <= it; ih++ ) {
				/* Equation (4) in Ref. 1 */
                mu_x = 1.0 / ( 1.0 + term * Math.abs ( ih - mu_0[it] ) );
                if ( !((mu_x  < 1e-06 ) || ( mu_x > 0.999999))) {
					/* Equation (6) & (8) in Ref. 1 */
                    ent += data[ih] * ( -mu_x * Math.log ( mu_x ) - ( 1.0 - mu_x ) * Math.log ( 1.0 - mu_x ) );
                }
            }

            for ( ih = it + 1; ih < 256; ih++ ) {
				/* Equation (4) in Ref. 1 */
                mu_x = 1.0 / ( 1.0 + term * Math.abs ( ih - mu_1[it] ) );
                if ( !((mu_x  < 1e-06 ) || ( mu_x > 0.999999))) {
					/* Equation (6) & (8) in Ref. 1 */
                    ent += data[ih] * ( -mu_x * Math.log ( mu_x ) - ( 1.0 - mu_x ) * Math.log ( 1.0 - mu_x ) );
                }
            }
			/* No need to divide by NUM_ROWS * NUM_COLS * LOG(2) ! */
            if ( ent < min_ent ) {
                min_ent = ent;
                threshold = it;
            }
        }
        return threshold;
    }
}
