<#import "parts/common.ftl" as c>
<#import "parts/form.ftl" as f>
<@c.page>
    <div class="mb-1">
        Add new user
    </div>
    ${message!}
    <@f.form "/registration" true/>
</@c.page>