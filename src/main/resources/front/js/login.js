const signupButton = document.getElementById('signup-button'),
    loginButton = document.getElementById('login-button'),
    userForms = document.getElementById('user_options-forms')

signupButton.addEventListener('click', () => {
    userForms.classList.remove('bounceRight')
    userForms.classList.add('bounceLeft')
}, false)


loginButton.addEventListener('click', () => {
    userForms.classList.remove('bounceLeft')
    userForms.classList.add('bounceRight')
}, false)


const findById = id => document.getElementById(id).value
const loginConfirmButton = document.getElementById("login_confirm_btn")
const registrationConfirmButton = document.getElementById("registration_confirm_btn")

loginConfirmButton.addEventListener('click', (e) => {
    e.preventDefault()

    let password = findById("l_password")
    let email = findById("l_email")

    fetch('http://localhost:8888/api/v1/user/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
                email: email,
                password: password
            }
        )
    })
        .then(res => res.json())
        .then(res => console.log(res))

}, false)

registrationConfirmButton.addEventListener('click', (e) => {
    e.preventDefault()
    let password = findById("r_password")
    let email = findById("l_email")

    fetch('http://localhost:8888/api/v1/user/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
                email: email,
                password: password
            }
        )
    })
        .then(res => res.json())
        .then(res => console.log(res))

}, true)