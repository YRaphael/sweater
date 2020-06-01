<#import "parts/common.ftl" as c>
<#import "parts/form.ftl" as f>
<@c.page>
Add new user
${message!}
<@f.form "/registration"/>
</@c.page>