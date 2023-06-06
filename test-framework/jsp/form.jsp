<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="/jean/submit" method="post">
        <input type="text" placeholder="name" name="name">
        <div>
            Liked color :
            <input type="checkbox" id="blue" name="colors" value="blue">
            <label for="blue">Blue</label>

            <input type="checkbox" id="black" name="colors" value="black">
            <label for="blue">Black</label>

            <input type="checkbox" id="black" name="colors" value="white">
            <label for="blue">White</label>

            <input type="checkbox" id="red" name="colors" value="red">
            <label for="blue">red</label>
        </div><br>
        
        <div>
            Liked digit :
            <% for(int i = 0; i < 10; ++i) { %>
                <input type="checkbox" id="digit_<%=i%>" name="digits" value="<%=i%>">
                <label for="digit_<%=i%>"><%=i%></label>
            <% } %>
        </div>

        <input type="submit" value="submit">
    </form>
</body>
</html>