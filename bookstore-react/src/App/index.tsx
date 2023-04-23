import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Main from '../pages/Main';

import './styles.scss';

const App = () => {
  return (
    <div className="app">
      <BrowserRouter>
        <Routes>
          <Route path="/*" element={<Main />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
};

export default App;
