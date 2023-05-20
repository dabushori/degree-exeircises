#include <stdio.h>

#include <sys/time.h>
#include <sys/resource.h>

int divide1(int x, double *t)
{
    struct timeval startTime;
    struct timeval endTime;
    struct rusage ru;
    getrusage(RUSAGE_SELF, &ru); // start timer
    startTime = ru.ru_utime;

    int q = x - (x >> 3);
    q = q + (q >> 6);
    q = q + (q >> 12) + (q >> 24);
    q = q >> 3;
    int r = x - (((q << 2) << 1) + q);
    int res = q + ((r + 7) >> 4);

    getrusage(RUSAGE_SELF, &ru); // end timer
    endTime = ru.ru_utime;
    double tS = startTime.tv_sec * 1000000.0 + (startTime.tv_usec);
    double tE = endTime.tv_sec * 1000000.0 + (endTime.tv_usec);
    // printf("Total runtime divide1: %f ms\n", (tE - tS) / 1000.0);
    *t = (tE - tS) / 1000.0;

    return res;
}

int divide2(int x, double *t)
{
    struct timeval startTime;
    struct timeval endTime;
    struct rusage ru;
    getrusage(RUSAGE_SELF, &ru); // start timer
    startTime = ru.ru_utime;

    int res = x / 9;

    getrusage(RUSAGE_SELF, &ru); // end timer
    endTime = ru.ru_utime;
    double tS = startTime.tv_sec * 1000000.0 + (startTime.tv_usec);
    double tE = endTime.tv_sec * 1000000.0 + (endTime.tv_usec);
    // printf("Total runtime divide2: %f ms\n", (tE - tS) / 1000.0);
    *t = (tE - tS) / 1000.0;

    return res;
}

#include <stdlib.h>

int main(int argc, char *argv[])
{
    int i = atoi(argv[0]);
    printf("%d\n", i) double time1, time2;
    int res1 = divide1(i, &time1), res2 = divide2(i, &time2);
    if (res1 == res2)
    {
        printf("%s is better\n", (time1 <= time2 ? (time1 < time2 ? "divide1" : "no one") : "divide2"));
    }
    else
    {
        printf("Error %d\n", i);
    }
}