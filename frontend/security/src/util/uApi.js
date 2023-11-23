const BASE = 'http://localhost:8080'

export default {
  // TEST //
  CONTENT_BASE: BASE + '/api/test',
  CONTENT_ALL: BASE + '/api/test/all',
  CONTENT_USER: BASE + '/api/test/user',

  // AUTHENTICATION //
  SIGNIN: BASE + '/api/auth/signin',
  SIGNUP: BASE + '/api/auth/signup',
  SIGNOUT: BASE + '/api/auth/signout',
  GENERATE_TOKEN_REFRESH: BASE + '/api/auth/generateRefresh',
  REFRESH_TOKEN: BASE + '/api/auth/refreshtoken'
}
