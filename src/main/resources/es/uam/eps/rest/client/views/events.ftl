<#-- @ftlvariable name="" type="EventsView" -->

<html>
    <head>
        <title>Events</title>
    </head>
    <body>
        <h1>Events</h1>
        <p>List of events</p>

        <table>
            <tr>	
                <th>Id</th>
                <th>User</th>
                <th>Item</th>
                <th>Type</th>
                <th>Value</th>
                <th>Timestamp</th>
            </tr>
            <#list events as event>
            <tr>
                <td>${event.eid}</td>
                <td>${event.uid}</td>
                <td>${event.iid}</td>
                <td>${event.type}</td>
                <td>${event.value}</td>
                <td>${event.timestamp}</td>
            </tr>
            </#list>
        </table>

        <p><a href="../index">Index</a></p>
    </body>
</html>
