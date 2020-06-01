<#import "parts/common.ftl" as c>
<#import "parts/form.ftl" as f>
<@c.page>
    <div>
        <@f.logout/>
    </div>
    <div>
        <form method="post" action="/main">
            <input type="text" name="text" placeholder="Введите сообщение">
            <input type="text" name="tag" placeholder="Тэг">
            <div><input type="hidden" name="_csrf" value="${_csrf.token}"></div>
            <button type="submit">Добавить</button>
        </form>
    </div>

    <div>Список сообщений:</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter!}">
        <button type="submit">Найти</button>
    </form>
    <#list messages as message>
    <div>
        <b>${message.id}</b>
        <span>${message.text}</span>
        <i>${message.tag}</i>
        <strong>${message.authorName}</strong>
    </div>
    <#else>
    No messages
    </#list >
</@c.page>