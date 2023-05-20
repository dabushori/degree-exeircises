#ifndef __GENERIC_SORT_H
#define __GENERIC_SORT_H

void sort(void* arr, int size, int(*compare)(void*, int, int), void(*swap)(void*, int, int));

#endif