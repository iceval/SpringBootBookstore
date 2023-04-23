import { useEffect, useState } from 'react';
import { BookProps } from '../../interfaces/book.interface';

import './styles.scss';

interface BookTableProps {
  showModal: () => void;
  setBookId: (bookId: number | undefined) => void;
}

function BookTable({ showModal, setBookId }: BookTableProps) {
  const [books, setBooks] = useState<BookProps[]>();

  const deleteData = async (id: number | undefined) => {
    if (id == undefined) return;

    await fetch(process.env.REACT_APP_API + 'books/' + Number(id), {
      method: 'DELETE',
    });

    window.location.reload();
  };

  const fetchData = async () => {
    await fetch(process.env.REACT_APP_API + 'books')
      .then((resp: Response) => {
        return resp.json();
      })
      .then((data: BookProps[]) => {
        setBooks(data);
      })
      .catch((e: Error) => {
        console.log(e.message);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  const changeBook = (id: number | undefined) => {
    setBookId(id);
    showModal();
  };

  return (
    <table className="book-table">
      <tbody>
        <tr>
          <th className="book-table__item book-table__topic">Автор</th>
          <th className="book-table__item book-table__topic">Название</th>
          <th className="book-table__item book-table__topic">Дата издания</th>
          <th></th>
          <th></th>
        </tr>
        {books?.map((book: BookProps) => (
          <tr key={book.id} className="book-table__book">
            <td className="book-table__item book-table__data">{book.author}</td>
            <td className="book-table__item book-table__data">{book.title}</td>
            <td className="book-table__item book-table__data">{book.date}</td>
            <td className="book-table__item">
              <button
                onClick={() => {
                  changeBook(book.id);
                }}
                className="book-table__button book-table__button-change"
              >
                Поменять Данные
              </button>
            </td>
            <td className="book-table__item">
              <button
                onClick={() => {
                  deleteData(book.id);
                }}
                className="book-table__button book-table__button-buy"
              >
                Купить
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default BookTable;
