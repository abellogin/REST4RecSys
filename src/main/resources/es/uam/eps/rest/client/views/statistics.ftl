<#-- @ftlvariable name="" type="StatisticsView" -->

<html>
    <head>
        <title>Statistics</title>
    </head>
    <body>
        <h1>Statistics of the system</h1>

        <table>
            <tr>	
                <th>Users</th>
                <th>Items</th>
                <th>Events</th>
                <th>Rating events</th>
                <th>Count events</th>
            </tr>
            <tr>
                <td>${stats.numUsers}</td>
                <td>${stats.numItems}</td>
                <td>${stats.numEvents}</td>
                <td>${stats.numEventRating}</td>
                <td>${stats.numEventCount}</td>
            </tr>
        </table>

        <p><a href="../index">Index</a></p>
    </body>
</html>
