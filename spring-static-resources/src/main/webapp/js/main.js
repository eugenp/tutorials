/**
 *
 */
require.config({
    baseUrl: 'js',

});
requirejs(["helpers/utils"], function (Util) {
    console.log(Util.helloWorld);
});