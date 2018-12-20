const express = require('express')
const app = express()
const morgan = require('morgan')
const mysql = require('mysql') 
const checkRouter = express('./routes/check')
const post = express('./routes/post')
const diffroute = express('./route/mobile')
const bodyParser = require('body-parser')
const util = require('util');

app.use(morgan("combined"))
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({
    extended: false
}));

var foo = bodyParser.text({ type: 'application/json' })
var android_pass
var android_user
var stored_pass
var stored_user
var verification = false

//Need to clean up SQL queries. For username, as long as the characters infront of the string is correct it will still login.
//Ex. Username: 1926 will still go for the wrong username of : 1926dhawudh

var connection = mysql.createConnection({ // Sql conenction. no need for connection.connect
    host:'127.0.0.1',
    user:'demo',
    port:3306,
    password:'password',
    database:'employee_db'
}) 


app.use('/check/:employee_idFK', (req,res)=> { //This route is for website
    console.log("Getting : " + req.params.employee_idFK)

        const queryString = 'SELECT * FROM employees WHERE employee_id = ?'
        connection.query(queryString, [req.params.employee_idFK], function (err, results, fields){
            res.json(results)
    })
})

app.post('/post', foo, (req,res) =>{// Getting variable from Android through HTTP POST method   
    
        var post = req.body
        res.setHeader('Content-Type', 'application/json ; charset=UTF-8')

        android_pass = req.body.passwords
        android_user = req.body.employee_idFK

        const queryString = 'SELECT * FROM employee_pass WHERE employee_idFK = ?'
        connection.query(queryString, [android_user], function (err, results, fields){

            if(results.length == 0){// If there is an error with the login, there should be nothing in results and we can jump out of the function
                return
            }

            stored_user = results[0].employee_idFK
            stored_pass = results[0].passwords
            console.log("This is the stored password and username" + stored_pass + "\n" + stored_user)
            console.log("This is the android password" + android_pass + "\n" + android_user)

            if (stored_pass == android_pass){ // Verifying user
                console.log("They are equal!")
                verification = true 
            }else{
                console.log("They are not equal")
            }
    }) 
})


app.use('/mobile', (req, res) =>{ //Can only be used after POST is called and user is verified

        if (verification == true){
            const queryString = 'SELECT * FROM employees WHERE employee_id = ?'
            connection.query(queryString, [android_user], function (err, results, fields){

            var emp_Buffer = results[0].employee_name //Employee name was stored as Buffer object. Accessing that object and storing into variable
            var emp_Fixed = emp_Buffer.toString() // Converting Buffer object into a string

            results[0].employee_name = emp_Fixed // Replacing that Buffer object with its string equivalent
            verification = false
            console.log ("Verfied")
            res.json(results) // Sends salary information for specified person
        })
        
    }else{
        console.log("Not verified")
    }

})

/**** Front-end Web Stuff ****/
/**** Not Implemented ****/
app.use(express.static(__dirname + '/html'))
app.use(express.static(__dirname + '/scripts'))

app.get('/' , (req,res,next) =>{
    console.log ('Responding')
    console.log (typeof (res))
    res.sendFile('index.html')
})
app.listen(3003, () =>{
    console.log('Listening to port 3003?')
})

