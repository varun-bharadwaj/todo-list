/* src/App.js */
import React, { useEffect, useState } from 'react'
import Amplify, { API, graphqlOperation } from 'aws-amplify'
import { DataStore } from '@aws-amplify/datastore';
import { Todo } from './models';

import awsExports from "./aws-exports";
Amplify.configure(awsExports);
const App = () => {
  const models =  DataStore.query(Todo);
  console.log(models);
}
export default App