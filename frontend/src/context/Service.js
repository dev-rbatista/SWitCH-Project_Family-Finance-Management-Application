// export const URL_API = 'http://localhost:8080'
//const [data, setData] = useState([]);
import {NodePath as Axios} from "@babel/traverse";

export const URL_API = 'http://localhost:8080'

/*
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: 'React POST Request Example' })
    };
    fetch('https://reqres.in/api/posts', requestOptions)
        .then(response => response.json())
        .then(data => this.setState({ postId: data.id }));
}
 */

export function fetchProfileFromWS(success, failure, id) {
    //neste momento está hardcoded mas será para ir buscar o id do loggeduser
    let urla = "http://localhost:8080/people/tonyze@latinlover.com";
    let url = "http://localhost:8080/people/{{id}}";
    Axios.get(`${url}`)
        .then((response) => {
            success(response)
            console.log(response);
        })

        .catch((err) => {
            failure(err)
            console.log(err);
        })
    ;
}

export function familyOptions(success, failure) {
    const requestOptions = {
        method: 'OPTIONS',
    }

    fetch(`${URL_API}/family`, requestOptions)
        .then(res => res.json())
        .then(res => {
            const allowedTypes = res.headers.get('Allow');
            success(allowedTypes)
        })
        .catch(err => failure(err.message))
}




export function familyRelationsFA(success, failure, familyId) {

    fetch(`${URL_API}/families/${familyId}/relations`)
        .then((res) => {
            console.log(res);
            return res.json()
        })
        .then((res) => {
            console.log(res);
            return success(res)
        })
        .catch((err) => {
            console.log(err.message);
            failure(err.message)
        })
    ;
}