Session 1
user-authentication
 - create user table
 - declare dependencies
 - configure data source
 - code User and (UserRepository extends JpaRepository)
 - implement (MyUserDetailsService extends UserDetails) and (UserPrincipal extends UserDetailsService)
 - configure authentication (AppSecurityConfig extends WebSecurityConfigurerAdapter)

Session 2
Role-base authorization
 - create role and user_role tables
 - Code Role
 - Code user_role relationship on user class
 