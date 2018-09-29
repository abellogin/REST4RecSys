<#-- @ftlvariable name="" type="AddItemView" -->

<html>
    <head>
        <title>Add item</title>
    </head>
    <body>
        <form action="/local_interface/add/item" method="post">
            Name:
            <br>
            <input type="text" name="name" value="name">
            <br>
            Content:
            <br>
            <input type="text" name="content" value="content">
            <br><br>			

        <input type="submit" value="Add this item">
        </form>
    </body>
</html>
