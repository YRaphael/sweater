<#macro form path isRegisterForm>
    <form action="${path}" method="post" xmlns="http://www.w3.org/1999/html">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name : </label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder="Username"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <#if isRegisterForm>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Email: </label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="some@some.com"/>
            </div>
        </div>
        </#if>
        <div><input type="hidden" name="_csrf" value="${_csrf.token}"></div>
        <div>
            <button class="btn btn-primary" type="submit"/><#if path == "/login">Login<#else>Sign Up</#if></button>
        </div>
        <#if !isRegisterForm>
            <a href="/registration">Add new user</a>
        </#if>
    </form>
</#macro>

<#macro logout >
    <form action="/logout" method="post">
        <button type="submit" class="btn btn-primary">Sign Out</button>
        <div><input type="hidden" name="_csrf" value="${_csrf.token}"></div>
    </form>
</#macro>