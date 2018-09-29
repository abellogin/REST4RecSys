<#-- @ftlvariable name="" type="AddUserView" -->

<html>
    <head>
        <title>Add user</title>
    </head>
    <body>
        <form action="/local_interface/add/user" method="post">
            User name:
            <br>
            <input type="text" name="name" value="name">
            <br>
            Location:
            <br>
            <input type="text" name="location" value="location">
            <br>
            Email:
            <br>
            <input type="text" name="email" value="email">
            <br>
            Age:
            <br>			
            <input type="text" name="age" value="age">
            <br><br>

        <input type="submit" value="Add this user">
        </form>
    </body>
</html>
