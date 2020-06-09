<#import "parts/common.ftl" as c>

<@c.page>
    <h5>${username}</h5>
    <form method="post" xmlns="http://www.w3.org/1999/html">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password confirmation: </label>
            <div class="col-sm-6">
                <input type="password" name="password2"
                       class="form-control ${(password2Error??)?string('is-invalid', '')}"
                       placeholder="Retype password"/>
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Email: </label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="some@some.com" value="${email!""}"/>
            </div>
        </div>
        <div><input type="hidden" name="_csrf" value="${_csrf.token}"></div>
        <div>
            <button class="btn btn-primary" type="submit"/>
            Save</button>
        </div>
    </form>
</@c.page>