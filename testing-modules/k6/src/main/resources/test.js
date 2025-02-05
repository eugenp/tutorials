import http from 'k6/http';
import { sleep, check } from 'k6';

// test configuration
export const options = {
    vus: 10, // simulate concurrent virtual users
    duration: '30s',

    thresholds: {
        http_req_duration: ['p(95)<500'], // 95% of requests must complete under 500ms
        http_req_failed: ['rate<0.01']    // less than 1% request failure rate
    }
};

// test scenario
export default function() {

    // simulate request
    const response = http.get('https://test-api.k6.io');

    // validate response
    check(response, {
        'status is 200': (r) => r.status === 200,
        'response time is acceptable': (r) => r.timings.duration < 500
    });

    // simulate user activity
    sleep(1);
}