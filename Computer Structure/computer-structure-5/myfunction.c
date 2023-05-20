// 212945760 Ori Dabush

#include <stdbool.h>

typedef struct
{
	unsigned char red;
	unsigned char green;
	unsigned char blue;
} pixel;

typedef struct
{
	int red;
	int green;
	int blue;
	// int num;
} pixel_sum;

/*
 * I did a number of things to optimize my code.
 * In addition to all the loop unrolling that I did, I also inlined some functions (which saved the stack accessing several times),
 * I calculated constant values before the loops (to avoid calculating them multiple times),
 * I used addition and shifting instead of multiplication,
 * I used register variables sometimes to assure they won't be read from the memory every time (they used very often).
 * I summurized here everything, Instead of the loop unrolling which will be explained before the functions.
 */

void myfunction(Image *image, char *srcImgpName, char *blurRsltImgName, char *sharpRsltImgName, char *filteredBlurRsltImgName, char *filteredSharpRsltImgName, char flag)
{
	int mns = m * n * sizeof(pixel);
	// allocate space for a dest pixels array
	pixel *dst = malloc(mns);
	// set src to be the data array of the image (which can be viewed as a pixels array instead of using charsToPixels)
	pixel *src = (pixel *)image->data;
	int dim = m;

	// copying only the frame of the image to dst because the rest is gonna be changed anyway
	int index, mn1 = n * (m - 1);
	pixel *last_row_src = src + mn1, *last_row_dst = dst + mn1;
	for (index = 0; index < m; ++index)
	{
		dst[index] = src[index];
		last_row_dst[index] = last_row_src[index];
	}
	int mindex = m, last_index = 2 * n - 1;
	for (index = 1; index < n; ++index)
	{
		dst[mindex] = src[mindex];
		dst[last_index] = src[last_index];
		last_index += m;
		mindex += m;
	}

	if (flag == '1')
	{
		// blur image
		// instead of calling smoothBlurNine(m, src, dst);

		// like smooth (from the given code) but with: int kernelSize = 3, int kernel[kernelSize][kernelSize] = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}},
		//											   kernelScale = 9, filter = false
		// (avoid the calculations when the parameters are known)
		// In this function I also did loop unrolling to optimize the time - just like in the other function (smoothSharp)

		int i, j, limit = dim - 1;
		pixel_sum col1 = {}, col2 = {}, col3 = {};
		register int idim = dim;
		for (i = 1; i < limit; ++i)
		{
			pixel *first_row = src + idim - dim, *second_row = src + idim, *third_row = src + idim + dim;

			register int currIndex = 0;

			pixel up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
			col1.red = up.red + mid.red + down.red;
			col1.green = up.green + mid.green + down.green;
			col1.blue = up.blue + mid.blue + down.blue;

			++currIndex;

			up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
			col2.red = up.red + mid.red + down.red;
			col2.green = up.green + mid.green + down.green;
			col2.blue = up.blue + mid.blue + down.blue;

			++currIndex;

			pixel sum;
			for (j = 5; j <= dim; j += 3)
			{
				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col3.red = up.red + mid.red + down.red;
				col3.green = up.green + mid.green + down.green;
				col3.blue = up.blue + mid.blue + down.blue;

				sum.red = (col1.red + col2.red + col3.red) / 9;
				sum.green = (col1.green + col2.green + col3.green) / 9;
				sum.blue = (col1.blue + col2.blue + col3.blue) / 9;
				dst[idim + currIndex - 1] = sum;

				++currIndex;

				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col1.red = up.red + mid.red + down.red;
				col1.green = up.green + mid.green + down.green;
				col1.blue = up.blue + mid.blue + down.blue;

				sum.red = (col1.red + col2.red + col3.red) / 9;
				sum.green = (col1.green + col2.green + col3.green) / 9;
				sum.blue = (col1.blue + col2.blue + col3.blue) / 9;
				dst[idim + currIndex - 1] = sum;

				++currIndex;

				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col2.red = up.red + mid.red + down.red;
				col2.green = up.green + mid.green + down.green;
				col2.blue = up.blue + mid.blue + down.blue;

				sum.red = (col1.red + col2.red + col3.red) / 9;
				sum.green = (col1.green + col2.green + col3.green) / 9;
				sum.blue = (col1.blue + col2.blue + col3.blue) / 9;
				dst[idim + currIndex - 1] = sum;

				++currIndex;
			}

			// the reminder (1 or 2 pixels)
			if (currIndex != dim)
			{
				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col3.red = up.red + mid.red + down.red;
				col3.green = up.green + mid.green + down.green;
				col3.blue = up.blue + mid.blue + down.blue;

				sum.red = (col1.red + col2.red + col3.red) / 9;
				sum.green = (col1.green + col2.green + col3.green) / 9;
				sum.blue = (col1.blue + col2.blue + col3.blue) / 9;
				dst[idim + currIndex - 1] = sum;

				++currIndex;

				if (currIndex != dim)
				{
					up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
					col1.red = up.red + mid.red + down.red;
					col1.green = up.green + mid.green + down.green;
					col1.blue = up.blue + mid.blue + down.blue;

					sum.red = (col1.red + col2.red + col3.red) / 9;
					sum.green = (col1.green + col2.green + col3.green) / 9;
					sum.blue = (col1.blue + col2.blue + col3.blue) / 9;
					dst[idim + currIndex - 1] = sum;

					// no need to increment currIndex here
				}
			}

			// here currIndex == dim for sure (we covered the reminder)

			idim += dim;
		}

		// set the image's data to be dst
		image->data = (char *)dst;
		// write result image to file
		writeBMP(image, srcImgpName, blurRsltImgName);
		// set the image's data back to src
		image->data = (char *)src;

		// sharpen the resulting image
		// instead of calling smoothSharp(m, dst, src);
		// pay attention that this function is called with src as dst and dst as src so I needed to switch the valiable's name

		// like smooth (fromt the given code) but with: int kernelSize = 3, int kernel[kernelSize][kernelSize] = {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}},
		//	 											kernelScale = 1, filter = false
		// (avoid the calculations when the parameters are known)
		// in this function I used loop unrolling - instead of calculateing the sum of the pixel with the weigths for every pixels I calculated it for
		// every column and used the same info for multiple pixels
		// also we don't need to re-declare i, j and limit because we have them from before with the values we want
		// and also col1, col2, col3 are re-declared
		idim = dim;
		for (i = 1; i < limit; ++i)
		{
			pixel *first_row = dst + idim - dim, *second_row = dst + idim, *third_row = dst + idim + dim;

			register int currIndex = 0;

			// calculate the first 2 columns
			pixel up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
			col1.red = up.red + mid.red + down.red;
			col1.green = up.green + mid.green + down.green;
			col1.blue = up.blue + mid.blue + down.blue;

			++currIndex;

			up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
			pixel center = mid;
			col2.red = up.red + mid.red + down.red;
			col2.green = up.green + mid.green + down.green;
			col2.blue = up.blue + mid.blue + down.blue;

			++currIndex;

			pixel sum;
			int tmp;
			// in every loop iteration, we calculate the next 3 columns and sum 3 every time to get the pixel we wnat.
			// every iteration we calculate 3 pixels in dst, instead of one.
			for (j = 5; j <= dim; j += 3)
			{
				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col3.red = up.red + mid.red + down.red;
				col3.green = up.green + mid.green + down.green;
				col3.blue = up.blue + mid.blue + down.blue;

				tmp = -(col1.red + col2.red + col3.red) + ((center.red << 3) + (center.red << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.red = (tmp > 0 ? tmp : 0);

				tmp = -(col1.green + col2.green + col3.green) + ((center.green << 3) + (center.green << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.green = (tmp > 0 ? tmp : 0);

				tmp = -(col1.blue + col2.blue + col3.blue) + ((center.blue << 3) + (center.blue << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.blue = (tmp > 0 ? tmp : 0);

				src[idim + currIndex - 1] = sum;

				center = mid;

				++currIndex;

				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col1.red = up.red + mid.red + down.red;
				col1.green = up.green + mid.green + down.green;
				col1.blue = up.blue + mid.blue + down.blue;

				tmp = -(col1.red + col2.red + col3.red) + ((center.red << 3) + (center.red << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.red = (tmp > 0 ? tmp : 0);

				tmp = -(col1.green + col2.green + col3.green) + ((center.green << 3) + (center.green << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.green = (tmp > 0 ? tmp : 0);

				tmp = -(col1.blue + col2.blue + col3.blue) + ((center.blue << 3) + (center.blue << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.blue = (tmp > 0 ? tmp : 0);

				src[idim + currIndex - 1] = sum;

				center = mid;

				++currIndex;

				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col2.red = up.red + mid.red + down.red;
				col2.green = up.green + mid.green + down.green;
				col2.blue = up.blue + mid.blue + down.blue;

				tmp = -(col1.red + col2.red + col3.red) + ((center.red << 3) + (center.red << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.red = (tmp > 0 ? tmp : 0);

				tmp = -(col1.green + col2.green + col3.green) + ((center.green << 3) + (center.green << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.green = (tmp > 0 ? tmp : 0);

				tmp = -(col1.blue + col2.blue + col3.blue) + ((center.blue << 3) + (center.blue << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.blue = (tmp > 0 ? tmp : 0);

				src[idim + currIndex - 1] = sum;

				center = mid;

				++currIndex;
			}

			// the reminder pixels (1 or 2 pixels)
			if (currIndex != dim)
			{
				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col3.red = up.red + mid.red + down.red;
				col3.green = up.green + mid.green + down.green;
				col3.blue = up.blue + mid.blue + down.blue;

				tmp = -(col1.red + col2.red + col3.red) + ((center.red << 3) + (center.red << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.red = (tmp > 0 ? tmp : 0);

				tmp = -(col1.green + col2.green + col3.green) + ((center.green << 3) + (center.green << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.green = (tmp > 0 ? tmp : 0);

				tmp = -(col1.blue + col2.blue + col3.blue) + ((center.blue << 3) + (center.blue << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.blue = (tmp > 0 ? tmp : 0);

				src[idim + currIndex - 1] = sum;

				center = mid;

				++currIndex;

				if (currIndex != dim)
				{
					up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
					col1.red = up.red + mid.red + down.red;
					col1.green = up.green + mid.green + down.green;
					col1.blue = up.blue + mid.blue + down.blue;

					tmp = -(col1.red + col2.red + col3.red) + ((center.red << 3) + (center.red << 1));
					tmp = (tmp < 255 ? tmp : 255);
					sum.red = (tmp > 0 ? tmp : 0);

					tmp = -(col1.green + col2.green + col3.green) + ((center.green << 3) + (center.green << 1));
					tmp = (tmp < 255 ? tmp : 255);
					sum.green = (tmp > 0 ? tmp : 0);

					tmp = -(col1.blue + col2.blue + col3.blue) + ((center.blue << 3) + (center.blue << 1));
					tmp = (tmp < 255 ? tmp : 255);
					sum.blue = (tmp > 0 ? tmp : 0);

					src[idim + currIndex - 1] = sum;

					center = mid;

					// no need to increment currIndex here
				}
			}

			// here currIndex == dim for sure (we covered the reminder and finished the row)

			idim += dim;
		}

		// write result image to file
		writeBMP(image, srcImgpName, sharpRsltImgName);
	}
	else
	{
		// apply extermum filtered kernel to blur image
		// instead of calling smoothBlurSeven(m, src, dst)
		//
		// like smooth (from the given code) but with:  int kernelSize = 3, int kernel[kernelSize][kernelSize] = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}},
		// 												kernelScale = 7, filter = true
		// (avoid the calculations when the parameters are known)
		// I must say that I tried to do loop unrolling here too, but I faced a bug that I couldn't solve and gave up (after a whole day of work).
		int i, j, idim = dim, limit = dim - 1;
		for (i = 1; i < limit; ++i)
		{
			for (j = 1; j < limit; ++j)
			{
				register int ii, jj;
				register int iidim = idim - dim;

				pixel_sum sum = {0};
				pixel current_pixel;

				int min_intensity = 766; // arbitrary value that is higher than maximum possible intensity, which is 255*3=765
				int max_intensity = -1;	 // arbitrary value that is lower than minimum possible intensity, which is 0
				int min_row_dim, min_col, max_row_dim, max_col;
				int ilimit = i + 1, jlimit = j + 1;
				for (ii = i - 1; ii <= ilimit; ++ii)
				{
					for (jj = j - 1; jj <= jlimit; ++jj)
					{
						current_pixel = src[iidim + jj];
						sum.red += ((int)current_pixel.red);
						sum.green += ((int)current_pixel.green);
						sum.blue += ((int)current_pixel.blue);

						int s = (((int)current_pixel.red) + ((int)current_pixel.green) + ((int)current_pixel.blue));
						if (s <= min_intensity)
						{
							min_intensity = s;
							min_row_dim = iidim;
							min_col = jj;
						}
						if (s > max_intensity)
						{
							max_intensity = s;
							max_row_dim = iidim;
							max_col = jj;
						}
					}
					iidim += dim;
				}

				// filter out min and max, without access the memory
				pixel min_val = src[min_row_dim + min_col], max_val = src[max_row_dim + max_col];
				sum.red -= min_val.red;
				sum.green -= min_val.green;
				sum.blue -= min_val.blue;
				sum.red -= max_val.red;
				sum.green -= max_val.green;
				sum.blue -= max_val.blue;

				// assign kernel's result to pixel at [i,j]
				sum.red /= 7;
				sum.green /= 7;
				sum.blue /= 7;

				// can't be more than 255 or less than 0 because it's the avg of the 7 pixels
				current_pixel.red = (unsigned char)sum.red;
				current_pixel.green = (unsigned char)sum.green;
				current_pixel.blue = (unsigned char)sum.blue;

				dst[idim + j] = current_pixel;
			}
			idim += dim;
		}

		// set the image's data to be dst
		image->data = (char *)dst;
		// write result image to file
		writeBMP(image, srcImgpName, filteredBlurRsltImgName);
		// set the image's data back to src
		image->data = (char *)src;

		// sharpen the resulting image
		// instead of calling smoothSharp(m, dst, src);
		// pay attention that this function is called with src as dst and dst as src so I needed to switch the valiable's name

		// like smooth (fromt the given code) but with: int kernelSize = 3, int kernel[kernelSize][kernelSize] = {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}},
		//	 											kernelScale = 1, filter = false
		// (avoid the calculations when the parameters are known)
		// in this function I used loop unrolling - instead of calculateing the sum of the pixel with the weigths for every pixels I calculated it for
		// every column and used the same info for multiple pixels
		// also we don't need to re-declare dim , i, j, limit because we have them from before with the values we want
		pixel_sum col1 = {}, col2 = {}, col3 = {};
		idim = dim;
		for (i = 1; i < limit; ++i)
		{
			pixel *first_row = dst + idim - dim, *second_row = dst + idim, *third_row = dst + idim + dim;

			register int currIndex = 0;

			// calculate the first 2 columns
			pixel up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
			col1.red = up.red + mid.red + down.red;
			col1.green = up.green + mid.green + down.green;
			col1.blue = up.blue + mid.blue + down.blue;

			++currIndex;

			up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
			pixel center = mid;
			col2.red = up.red + mid.red + down.red;
			col2.green = up.green + mid.green + down.green;
			col2.blue = up.blue + mid.blue + down.blue;

			++currIndex;

			pixel sum;
			int tmp;
			// in every loop iteration, we calculate the next 3 columns and sum 3 every time to get the pixel we wnat.
			// every iteration we calculate 3 pixels in dst, instead of one.
			for (j = 5; j <= dim; j += 3)
			{
				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col3.red = up.red + mid.red + down.red;
				col3.green = up.green + mid.green + down.green;
				col3.blue = up.blue + mid.blue + down.blue;

				tmp = -(col1.red + col2.red + col3.red) + ((center.red << 3) + (center.red << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.red = (tmp > 0 ? tmp : 0);

				tmp = -(col1.green + col2.green + col3.green) + ((center.green << 3) + (center.green << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.green = (tmp > 0 ? tmp : 0);

				tmp = -(col1.blue + col2.blue + col3.blue) + ((center.blue << 3) + (center.blue << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.blue = (tmp > 0 ? tmp : 0);

				src[idim + currIndex - 1] = sum;

				center = mid;

				++currIndex;

				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col1.red = up.red + mid.red + down.red;
				col1.green = up.green + mid.green + down.green;
				col1.blue = up.blue + mid.blue + down.blue;

				tmp = -(col1.red + col2.red + col3.red) + ((center.red << 3) + (center.red << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.red = (tmp > 0 ? tmp : 0);

				tmp = -(col1.green + col2.green + col3.green) + ((center.green << 3) + (center.green << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.green = (tmp > 0 ? tmp : 0);

				tmp = -(col1.blue + col2.blue + col3.blue) + ((center.blue << 3) + (center.blue << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.blue = (tmp > 0 ? tmp : 0);

				src[idim + currIndex - 1] = sum;

				center = mid;

				++currIndex;

				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col2.red = up.red + mid.red + down.red;
				col2.green = up.green + mid.green + down.green;
				col2.blue = up.blue + mid.blue + down.blue;

				tmp = -(col1.red + col2.red + col3.red) + ((center.red << 3) + (center.red << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.red = (tmp > 0 ? tmp : 0);

				tmp = -(col1.green + col2.green + col3.green) + ((center.green << 3) + (center.green << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.green = (tmp > 0 ? tmp : 0);

				tmp = -(col1.blue + col2.blue + col3.blue) + ((center.blue << 3) + (center.blue << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.blue = (tmp > 0 ? tmp : 0);

				src[idim + currIndex - 1] = sum;

				center = mid;

				++currIndex;
			}

			// the reminder pixels (1 or 2 pixels)
			if (currIndex != dim)
			{
				up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
				col3.red = up.red + mid.red + down.red;
				col3.green = up.green + mid.green + down.green;
				col3.blue = up.blue + mid.blue + down.blue;

				tmp = -(col1.red + col2.red + col3.red) + ((center.red << 3) + (center.red << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.red = (tmp > 0 ? tmp : 0);

				tmp = -(col1.green + col2.green + col3.green) + ((center.green << 3) + (center.green << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.green = (tmp > 0 ? tmp : 0);

				tmp = -(col1.blue + col2.blue + col3.blue) + ((center.blue << 3) + (center.blue << 1));
				tmp = (tmp < 255 ? tmp : 255);
				sum.blue = (tmp > 0 ? tmp : 0);

				src[idim + currIndex - 1] = sum;

				center = mid;

				++currIndex;

				if (currIndex != dim)
				{
					up = first_row[currIndex], mid = second_row[currIndex], down = third_row[currIndex];
					col1.red = up.red + mid.red + down.red;
					col1.green = up.green + mid.green + down.green;
					col1.blue = up.blue + mid.blue + down.blue;

					tmp = -(col1.red + col2.red + col3.red) + ((center.red << 3) + (center.red << 1));
					tmp = (tmp < 255 ? tmp : 255);
					sum.red = (tmp > 0 ? tmp : 0);

					tmp = -(col1.green + col2.green + col3.green) + ((center.green << 3) + (center.green << 1));
					tmp = (tmp < 255 ? tmp : 255);
					sum.green = (tmp > 0 ? tmp : 0);

					tmp = -(col1.blue + col2.blue + col3.blue) + ((center.blue << 3) + (center.blue << 1));
					tmp = (tmp < 255 ? tmp : 255);
					sum.blue = (tmp > 0 ? tmp : 0);

					src[idim + currIndex - 1] = sum;

					center = mid;

					// no need to increment currIndex here
				}
			}

			// here currIndex == dim for sure (we covered the reminder and finished the row)

			idim += dim;
		}

		// write result image to file
		writeBMP(image, srcImgpName, filteredSharpRsltImgName);
	}

	// free the memory we allocated
	free(dst);
}