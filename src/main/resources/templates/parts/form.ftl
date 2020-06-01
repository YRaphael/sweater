<#macro form path>
    <form action="${path}" method="post">
        <div><label> User Name : <input type="text" name="username"/> </label></div>
        <div><label> Password: <input type="password" name="password"/> </label></div>
        <div><input type="hidden" name="_csrf" value="${_csrf.token}"></div>
        <div><input type="submit" value=<#if path == "/login">Login<#else>Sign Up</#if> /></div>
    </form>
</#macro>

<#macro logout >
    <form action="/logout" method="post">
        <input type="submit" value="Sign Out"/>
        <div><input type="hidden" name="_csrf" value="${_csrf.token}"></div>
    </form>
</#macro>