import { API, graphqlOperation } from 'aws-amplify';
import * as mutations from './graphql/mutations'
import React, {useState, useEffect } from 'react'
import Amplify, {Auth } from 'aws-amplify'
import { DataStore } from '@aws-amplify/datastore';
import { Todo } from './models';
import { withAuthenticator } from '@aws-amplify/ui-react'
import awsExports from "./aws-exports";
import * as subscriptions from './graphql/subscriptions';

Amplify.configure(awsExports);

const initialState = { taskName: ''}
const initialUser = "epcvrn"


const App = () => {
  const [formState, setFormState] = useState(initialState)
  const[user, setUser] = useState(initialUser)
  const [todos, setTodos] = useState([])
  async function fetchTodos(){
    await DataStore.query(Todo, task=>task.user("eq", user).completed("ne", "True")).then(todos=>setTodos(todos));
  }
  useEffect(() =>{
    fetchTodos();
  })
  const subscription = DataStore.observe(Todo).subscribe();
  Auth.currentAuthenticatedUser({bypassCache: false}).then(user =>setUser(user.username)).catch(err => console.log(err));

  async function addTodo() {
    try {
      if (!formState.taskName) return
        const newItem = new Todo({
        "name": formState.taskName,
        completed: "False",
        "user": user
        })
        setTodos([todos, newItem])
        const newTodo = await API.graphql(graphqlOperation(mutations.createTodo, {input: newItem})).then(setFormState(initialState)); 
    } catch (err) {
      console.log('error creating todo:', err)
    }
  }

  function setInput(key, value) {
    setFormState({ ...formState, [key]: value })
  }

  async function signOut() {
    try {
       await Auth.signOut();
    } catch (error) {
        console.log('error signing out: ', error);
    }
    window.location.reload();
  }

async function removeItem(original){
  await DataStore.save(
    Todo.copyOf(original, updated => {
      updated.completed ="True";
    })
  );
  const origLocation = todos.indexOf(original);
  console.log(origLocation);
  console.log("Before: " + todos.length);
  todos.splice(origLocation, 1);
  
  console.log("After: : " + todos.length);
}


  return (
    <div style={styles.container}>
      <h2>Amplify Todos</h2>
      <input
        onChange={event => setInput('taskName', event.target.value)}
        style={styles.input}
        value={formState.taskName}
        placeholder="Name"
      />
      <button onClick={addTodo}>Create Todo</button>
      <button onClick = {signOut}>Sign Out</button>
      {
        todos.map((todo, index) => (
          <div key={todo.id ? todo.id : index} style={styles.todo}>
            <p style={styles.todoName}>{todo.name}</p>
            <button onClick={() => removeItem(todo)}>Remove</button>
          </div>
        ))
      }
    </div>
  )
}


const styles = {
  container: { width: 400, margin: '0 auto', display: 'flex', flexDirection: 'column', justifyContent: 'center', padding: 20 },
  todo: {  marginBottom: 15 },
  input: { border: 'none', backgroundColor: '#ddd', marginBottom: 10, padding: 8, fontSize: 18 },
  todoName: { fontSize: 20, fontWeight: 'bold' },
  todoDescription: { marginBottom: 0 },
  button: { backgroundColor: 'black', color: 'white', outline: 'none', fontSize: 18, padding: '12px 0px' }
}

export default withAuthenticator(App)
