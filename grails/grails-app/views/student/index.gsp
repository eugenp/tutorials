<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><g:link class="create" action="create">Create</g:link></li>
            </ul>
        </div>
        <div id="list-student" class="content scaffold-list" role="main">
            <f:table collection="${studentList}" properties="['firstName', 'lastName']" />
        </div>
    </body>
</html>