<#import "parts/common.ftl" as c>
<#import "parts/form.ftl" as f>
<@c.page>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <@f.form "/login" false/>
</@c.page>