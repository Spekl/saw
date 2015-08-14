#include <stdint.h>

#ifdef RUN
#include <stdio.h>
typedef unsigned int uint32_t;
#endif


uint32_t ffs_test(uint32_t i) {
    char n = 1;
    if (!(i & 0xffff)) { n += 16; i >>= 16; } 
    if (!(i & 0x00ff)) { n += 8;  i >>= 8; }
    if (!(i & 0x000f)) { n += 4;  i >>= 4; }
    if (!(i & 0x0003)) { n += 2;  i >>= 2; }
    return (i) ? (n+((i+1) & 0x01)) : 0;
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
