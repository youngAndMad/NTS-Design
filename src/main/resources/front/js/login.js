const signupButton = document.getElementById('signup-button')
const loginButton = document.getElementById('login-button')
const userForms = document.getElementById('user_options-forms')

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
        .then(res => set('user', res))
        .catch(err => alert(err))

}, false)

registrationConfirmButton.addEventListener('click', (e) => {
    e.preventDefault()
    let password = findById("r_password")
    let email = findById("l_email")

    fetch(`http://localhost:8888/api/v1/user/login`, {
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
        .then(res => set('user', res))
        .catch(err => alert(err))

}, true)

const set = (key, value) => localStorage.setItem(key, JSON.stringify(value))