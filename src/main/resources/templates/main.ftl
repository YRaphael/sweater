<#import "parts/common.ftl" as c>
<@c.page>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input class="form-control" type="text" name="filter" value="${filter!}" placeholder="Search by tag">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>

        </div>
    </div>


    <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample"
            aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </button>
    <div class="collapse form-group mt-3 <#if message??>show</#if>" id="collapseExample">
        <form method="post" action="/main" enctype="multipart/form-data">
            <div class="form-group">
                <input class="form-control ${(textError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.text}</#if>"
                       type="text" name="text"
                       placeholder="Message">
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input class="form-control ${(tagError??)?string('is-invalid', '')}"
                       type="text" name="tag"
                       placeholder="Tag"
                       value="<#if message??>${message.tag}</#if>">
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="customFile" name="multipartFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <div><input type="hidden" name="_csrf" value="${_csrf.token}"></div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Add</button>
            </div>
        </form>
    </div>

    <div class="card-columns">
        <#list messages as message>
            <div class="card my-3">
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                </div>
                <div class="m-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    <strong>${message.getAuthorName()}</strong>
                </div>
            </div>
        <#else>
            No messages
        </#list >
    </div>
</@c.page>