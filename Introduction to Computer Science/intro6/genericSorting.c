#include <stdio.h>
#include <string.h>

int compare_char(void* arr, int i, int j) {
    char* char_arr = (char*) arr;
    return char_arr[i] - char_arr[j];
}

int compare_int(void* arr, int i, int j) {
    int* int_arr = (int*) arr;
    return int_arr[i] - int_arr[j];
}

int compare_str(void* arr, int i, int j) {
    char** str_arr = (char**) arr;
    return strcmp(str_arr[i], str_arr[j]);
}

void swap_char(void* arr, int i, int j) {
    char* char_arr = (char*) arr;
    char tmp = char_arr[i];
    char_arr[i] = char_arr[j];
    char_arr[j] = tmp;
}

void swap_int(void* arr, int i, int j) {
    int* int_arr = (int*) arr;
    int tmp = int_arr[i];
    int_arr[i] = int_arr[j];
    int_arr[j] = tmp;
}

void swap_str(void* arr, int i, int j) {
    char** str_arr = (char**) arr;
    char* tmp = str_arr[i];
    str_arr[i] = str_arr[j];
    str_arr[j] = tmp;
}

void sort(void* arr, int size, int(*compare)(void*,int,int), void(*swap)(void*,int,int)) {
    for (int i = 0; i < size-1; i++) {
        for (int j = 0; j < size-1-i; j++) {
            if (compare(arr, j, j+1) > 0) {
                swap(arr, j, j+1);
            }
        }
    }
}

void print_char(void* arr, int i) {
    char* char_arr = (char*) arr;
    printf("%c", char_arr[i]);
}

void print_int(void* arr, int i) {
    int* int_arr = (int*) arr;
    printf("%d", int_arr[i]);
}

void print_str(void* arr, int i) {
    char** str_arr = (char**) arr;
    printf("%s", str_arr[i]);
}

void print_arr(void* arr, int size, void(*print)(void*, int)) {
    for (int i = 0; i < size; i++) {
        if (i) {
            printf(" ");
        }
        print(arr, i);
    }
    printf("\n");
}