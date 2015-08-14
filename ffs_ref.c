#include <stdint.h>

#ifdef RUN
#include <stdio.h>
typedef unsigned int uint32_t;
#endif

// returns the index of the first non-zero bit
uint32_t ffs_ref(uint32_t word) {
    int i = 0;
    if(!word)
        return 0;
    for(int cnt = 0; cnt < 32; cnt++)
        if(((1 << i++) & word) != 0)
            return i;
    return 0; // notreached
}



#ifdef RUN
int main(int argc, char **argv) {
    /* uint32_t t = 0x800000; */
    /* printf("(imp, ref) 0x%x -> (0x%x, 0x%x)\n", t, ffs_imp(t), ffs_ref(t)); */
    /* t = 0x000002; */
    /* printf("(imp, ref) 0x%x -> (0x%x, 0x%x)\n", t, ffs_imp(t), ffs_ref(t)); */
    /* t = 0x000008; */
    /* printf("(imp, ref) 0x%x -> (0x%x, 0x%x)\n", t, ffs_imp(t), ffs_ref(t)); */
    /* t = 0x101010; */
    /* printf("(bug, ref) 0x%x -> (0x%x, 0x%x)\n", t, ffs_bug(t), ffs_ref(t)); */
}
#endif

