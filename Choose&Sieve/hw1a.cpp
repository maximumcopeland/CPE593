/*
    Author: Max Copeland
    "I pledge my honor that I have abided by the Stevens Honor System"
    Utilizing bitvec code from Professor Kruger
*/
#include <iostream>
#include <cmath>
using namespace std;

class BitVec {
    private:
        uint32_t words;
        uint32_t size;
        uint64_t* bits;
        unsigned long int count;

    public:
        BitVec(uint32_t n, bool value) : words((n+63)/64), size(n), bits(new uint64_t[words]) {
            uint64_t v = value ? uint64_t(-1LL) : 0;
            for (uint32_t i = 0; i < words; i++)
            bits[i] = v;
        }
        BitVec(const BitVec& orig);
        void set(uint32_t i) {
            //  bits[i / 64] |= 1 << (i % 64);
            bits[i >> 6] |= 1L << (i & 0x3F);
        }

        void clear(uint32_t i) {
            bits[i >> 6] &= ~(1L << (i & 0x3F));
        }

        bool test(uint32_t i) {
            return (bits[i >> 6] & 1L << (i & 0x3F)) != 0;
        }
};

BitVec sieve(uint64_t b) {
    const uint64_t n = b+1;
    BitVec vec(n, true);
    // Skip special cases 0 and 1
    vec.clear(0);
    vec.clear(1);
    for (uint64_t i = 2; i <= sqrt(b); i++) {
        if (vec.test(i)) {
            for (uint64_t j = i*i; j <= b; j += i) {
                vec.clear(j);
            }
        }
    }
    return vec;
}

BitVec sieve2(uint64_t a, uint64_t b, BitVec vec) {
    const uint64_t n = b-a+1;
    BitVec vec2(n, true);
    // Skip special cases 0 and 1
    vec2.clear(0);
    vec2.clear(1);
    for (uint64_t i = a; i <= sqrt(b); i++) {
        if (vec.test(i)) {
            for (uint64_t j = i*i; j <= b; j += i) {
                vec2.clear(j);
            }
        }
    }
    return vec2;
}

uint64_t getPrimes(uint64_t a, uint64_t b, BitVec vec) {
    uint64_t count = 0;
    // Skip special cases 0 and 1
    for (uint64_t i = a; i <= b; i++) {
        if (vec.test(i)) {
            count++;
        }
    }
    return count;
}


int main() {
    uint64_t a;
    uint64_t b;
    cout << "Input 1: ";
    cin >> a;
    cout << endl;
    cout << "Input 2: ";
    cin >> b;
    cout << endl;

    BitVec vec = sieve(sqrt(b));
    BitVec vec2 = sieve2(a, b, vec);
    //allocate bitvec[b-a+1]
    //for all primes in vec
    //from 2 to sqrt(b)
    //if vec isprime 
    //clear multiples in vec2

    // Count the primes between a and b
    cout << "Output: " << getPrimes(0, b, vec2) << endl;
    return 0;
}