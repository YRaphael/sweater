<#import "parts/common.ftl" as c>
<#import "parts/form.ftl" as f>
<@c.page>
    ${message!}
    <@f.form "/login" false/>
</@c.page>