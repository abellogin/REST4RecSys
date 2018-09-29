<#-- @ftlvariable name="" type="AddEventView" -->

<html>
    <head>
        <title>Add event</title>
    </head>
    <body>
        <form action="/local_interface/add/event" method="post">
            User:
            <br>
            <input type="text" name="uid" value="uid">
            <br>
            Item:
            <br>
            <input type="text" name="iid" value="iid">
            <br><br>			
            Type:
            <br>
            <select name="type">
               <option value="rating" selected>Rating</option> 
               <option value="count">Count</option> 
            </select>
            <br><br>			
            Value:
            <br>
            <input type="text" name="value" value="1">
            <br><br>			

        <input type="submit" value="Add this event">
        </form>
    </body>
</html>
