import Form from '../Form';

import './styles.scss';

interface ModalProps {
  isShowModal: boolean;
  showModal: () => void;
  bookId: number | undefined;
}

function Modal({ isShowModal, showModal, bookId }: ModalProps) {
  return (
    <>
      {isShowModal && (
        <div className={`modal ${isShowModal ? 'active' : ''}`}>
          <Form showModal={showModal} id={bookId} />
        </div>
      )}
    </>
  );
}

export default Modal;
