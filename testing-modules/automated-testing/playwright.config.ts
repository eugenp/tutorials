import {devices,PlaywrightTestConfig } from '@playwright/test';


const config: PlaywrightTestConfig = {
    
    projects: [
        { 
            name: "chrome:latest:macOS Sonoma@lambdatest",
        
            use: {
                viewport: { width: 1920, height: 1080 },
            },
        },
        {
            name: "chrome:latest:Windows 10@lambdatest",
            use: {
                viewport: { width: 1280, height: 720 },
            },
        },
        {
            name: "Google Chrome",
            use: {
                ...devices["Desktop Chrome"],
                channel: "chrome"
            }
         },
        {
            name: "firefox",
            use: {
                ...devices["Desktop Firefox"]
            }
        }
    ],
    testDir: "./tests",

    use: {
        baseURL: "https://ecommerce-playground.lambdatest.io/",
        headless: false,
        screenshot: "on",
        video: "on",
        launchOptions: {
             slowMo: 100
        },
    },
    timeout: 60 * 1000 * 5,
    retries: 0,
    reporter: [["dot"], ["json", {
        outputFile: "jsonReports/jsonReport.json"
    }], ["html", {
        open: "never"
    }]]
};

export default config;