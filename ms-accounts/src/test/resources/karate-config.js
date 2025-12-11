function fn() {
    var config = {};
    var JwtUtil = Java.type('com.devsu.karate.JwtUtil');
    var jwt = JwtUtil.createToken();
    config.authHeader = 'Bearer ' + jwt;
    return config;
}
