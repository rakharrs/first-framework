<%
    String[] colors = (String[]) request.getAttribute("colors");        // récupération de l'attribut couleur des parametres
    Integer[] digits = (Integer[]) request.getAttribute("digits");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <div>Name : <%= request.getAttribute("name") %></div><br>
    <div>                  
        Liked colors : 
        <ul>
            <% if(colors != null){
                for(int i = 0; i < colors.length; ++i){ %>               
                <li><%=colors[i]%></li>
            <% } } %>
        </ul>
    </div>
    <div>
        Liked digits : 
        <ul>
            <% if(digits != null){
                for(int i = 0; i < digits.length; ++i){ %>               
                <li><%=digits[i]%></li>
            <% } } %>
        </ul>
    </div>
</body>
</html>