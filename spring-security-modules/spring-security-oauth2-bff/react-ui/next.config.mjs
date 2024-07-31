/** @type {import('next').NextConfig} */

const nextConfig = {
    output: "standalone",
    basePath: process.env.NEXT_PUBLIC_BASE_PATH,
    assetPrefix: '/react-ui',
};

export default nextConfig