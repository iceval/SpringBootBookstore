import { useState } from 'react';
import BookTable from '../../components/BookTable';
import Modal from '../../components/Modal';

import './styles.scss';

function Main() {
  const [isShowModal, setIsShowModal] = useState<boolean>(false);
  const [bookId, setBookId] = useState<number | undefined>();

  const showModal = () => {
    setIsShowModal((prev: boolean) => !prev);
  };

  const handleAdd = () => {
    setBookId(undefined);
    showModal();
  };

  return (
    <div className="main">
      <h1 className="main__title">Список книг</h1>
      <button onClick={handleAdd} className="main__button">
        Добавить
      </button>
      <BookTable showModal={showModal} setBookId={setBookId} />
      <Modal isShowModal={isShowModal} showModal={showModal} bookId={bookId} />
    </div>
  );
}

export default Main;
