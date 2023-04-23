import { ChangeEvent, useEffect, useState } from 'react';
import { BookProps } from '../../interfaces/book.interface';

import './styles.scss';

interface FormProps {
  showModal: () => void;
  id: number | undefined;
}

function Form({ showModal, id }: FormProps) {
  const [author, setAuthor] = useState<string>('');
  const [title, setTitle] = useState<string>('');
  const [date, setDate] = useState<string>('');

  const getDataBook = async () => {
    await fetch(process.env.REACT_APP_API + 'books/' + id)
      .then((resp: Response) => resp.json())
      .then((data: BookProps) => {
        setAuthor(data.author);
        setTitle(data.title);
        setDate(data.date);
      });
  };

  const postData = async () => {
    await fetch(process.env.REACT_APP_API + 'books', {
      method: 'POST',
      body: JSON.stringify({ author, title, date }),
      headers: { 'Content-Type': 'application/json' },
    });
  };

  const updateData = async () => {
    await fetch(process.env.REACT_APP_API + 'books/' + Number(id), {
      method: 'PUT',
      body: JSON.stringify({ id, author, title, date }),
      headers: { 'Content-Type': 'application/json' },
    });
  };

  const handleSubmit = () => {
    showModal();

    console.log(id);

    if (id != undefined) updateData();
    else postData();
    window.location.reload();
  };

  const handleBack = () => {
    showModal();
  };

  const handleChangeAuthor = (event: ChangeEvent<HTMLInputElement>) => {
    setAuthor(event.target.value);
  };

  const handleChangeTitle = (event: ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  const handleChangeDate = (event: ChangeEvent<HTMLInputElement>) => {
    setDate(event.target.value);
  };

  useEffect(() => {
    if (id != undefined) getDataBook();
  }, []);

  return (
    <form className="form" onSubmit={handleSubmit}>
      <div className="form__box">
        <button className="form__back" onClick={handleBack} />
        <div className="form__title">Author</div>
        <input
          name="author"
          type="text"
          placeholder="Введите автора"
          value={author}
          onChange={handleChangeAuthor}
          className="form__input"
        />
        <div className="form__title">Title</div>
        <input
          name="title"
          type="text"
          placeholder="Введите название"
          value={title}
          onChange={handleChangeTitle}
          className="form__input"
        />
        <div className="form__title">Date</div>
        <input
          name="date"
          type="date"
          placeholder="Введите дату"
          value={date}
          onChange={handleChangeDate}
          className="form__input"
        />
        <button type="submit" className="form__button">
          Отправить
        </button>
      </div>
    </form>
  );
}

export default Form;
