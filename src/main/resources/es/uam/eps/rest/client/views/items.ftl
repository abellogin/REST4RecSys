<#-- @ftlvariable name="" type="ItemsView" -->

<html>
    <head>
        <title>Items</title>
        </head>
    <body>
        <h1>Items</h1>
        <p>List of items</p>

        <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Content</th>
            </tr>
            <#list items as item>
            <tr>
                <td>${item.iid}</td>
                <td>${item.name}</td>
                <td>${item.content}</td>
            </tr>
            </#list>
        </table>

        <p><a href="../index"Index</a></p>
    </body>
</html>
