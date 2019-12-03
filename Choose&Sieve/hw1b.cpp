/*
    Author: Max Copeland
    "I pledge my honor that I have abided by the Stevens Honor System"
    Assistance with syntax from Brian Bazergui and Mark Matthews
*/
#include <iostream>
#include <random>
using namespace std;
double memo[501][501]; 

void zeroMemo() {
    for (int i = 0; i < 501; i++) {
        for (int j = 0; j < 501; j++) {
            memo[i][j] = 0;
        }
    }
}

double choose(int n, int r) {
    if (r == n || r == 0){
        return memo[n][r] = 1;
    }
    else if (memo[n][r] != 0){
        return memo[n][r];
    }
    else {
        return memo[n][r] = choose(n-1, r-1) + choose(n-1, r);
    }
    return 0;
}

int main() {
    int numTrials = 100000000;
    zeroMemo();
    //int numTrials;
	//cin >> numTrials;
	default_random_engine generator;
	uniform_int_distribution<int> distribution(0,500);	

    for (int i = 0; i < numTrials; i++) {
	    int n = distribution(generator);
		uniform_int_distribution<int> rdist(0,n);	
		int r = rdist(generator);
        cout << "n = " << n << ", r = " << r << ", " << choose(n,r) << endl;
        zeroMemo();
	}
    return 0;
}