<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><g:link class="list" action="index">Students list</g:link></li>
            </ul>
        </div>
        <div id="show-student" class="content scaffold-show" role="main">
            <f:display bean="student" />
            <g:form resource="${this.student}" method="DELETE">
                <fieldset class="buttons">
                    <input class="delete" type="submit" value="delete" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
