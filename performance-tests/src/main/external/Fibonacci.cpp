#include <iostream> 
#include <chrono> 

using namespace std;

int fibonacci(int n) {
    if (n <= 1)
        return n;
    return fibonacci(n - 1) + fibonacci(n - 2);
}

int main() {
    for (int i = 0; i < 100; i++) {
        auto startTime = chrono::high_resolution_clock::now().time_since_epoch();
        int result = fibonacci(12);
        auto totalTime = chrono::high_resolution_clock::now().time_since_epoch() - startTime;
        cout << totalTime << "\n";
    } 
}