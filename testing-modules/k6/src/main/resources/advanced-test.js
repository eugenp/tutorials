import http from 'k6/http';
import { sleep, check } from 'k6';
import { Counter } from 'k6/metrics';

// test configuration
export const options = {
    stages: [
        { duration: '1m', target: 20 }, // ramp up to 20 users in 1 minute
        { duration: '3m', target: 20 }, // hold steady for 3 minutes
        { duration: '1m', target: 0 },  // ramp down to 0 users in 1 minute
    ],
};

let loginFailures = new Counter('login_failures');

// test scenario
export default function() {

    // simulate diverse user interactions
    const responses = http.batch([
        ['GET', 'https://test-api.k6.io/public/crocodiles/'],
        ['POST', 'https://test-api.k6.io/auth/basic/login/', JSON.stringify({username:'test',password:'1234'})]
    ]);

    let res = http.post('https://test-api.k6.io/auth/basic/login/', { username: 'test', password: 'wrong_password' });
    if (res.status !== 200) {
        loginFailures.add(1); // Increment the counter for failed logins
    }

    // validate multiple endpoint responses
    check(responses[0], { 'Crocodiles loaded': (r) => r.status === 200 });
    check(responses[1], { 'Login done': (r) => r.status === 200 });
}