<#import "parts/common.ftl" as c>
<#import "parts/form.ftl" as f>
<@c.page>
    <div>
        <@f.logout/>
        <span><a href="/user">User list</a> </span>
    </div>
    <div>
        <form method="post" action="/main" enctype="multipart/form-data">
            <input type="text" name="text" placeholder="Введите сообщение">
            <input type="text" name="tag" placeholder="Тэг">
            <input type="file" name="multipartFile">
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
        <strong>${message.getAuthorName()}</strong>
        <div>
            <#if message.filename??>
                <img src="/img/${message.filename}">
            </#if>
        </div>
    </div>
    <#else>
    No messages
    </#list >
</@c.page>