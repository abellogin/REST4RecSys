<#-- @ftlvariable name="" type="UsersView" -->

<html>
    <head>
        <title>Users</title>
        </head>
    <body>
        <h1>Users</h1>
        <p>List of users</p>
        <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Location</th>
            </tr>
            <#list users as user>
            <tr>
                <td>${user.uid}</td>
                <td>${user.name}</td>
                <td>${user.location}</td>
            </tr>
            </#list>
        </table>

        <p><a href="../index"Index</a></p>
    </body>
</html>
