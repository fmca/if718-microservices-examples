class Config:
    db = None # has to be set in main.py

config = Config() # global config

from abc import ABC, abstractmethod
from dataclasses import dataclass

@dataclass
class Account:
    id: int
    name: str
    balance: int


class AccountController:

    def __init__(self) -> None:
        self.account_collection = AccountCollection()

    def create(self, account):
        self.account_collection.insert(account)

    def all(self):
        return self.account_collection.all()
    
    def has_enough_balance(self, account_id, value_to_be_deducted):
        balance = self.account_collection.get(account_id).balance
        return balance >= value_to_be_deducted

    def update_balance(self, account_id, value_to_be_appended):
        self.account_collection.update_balance(account_id, value_to_be_appended)

class AccountCollection:

    def __init__(self) -> None:
        self.repository: IRepository = SQLAlchemyRepository()
    
    def insert(self, account: Account):
        self.repository.insert(account)

    def all(self):
        return self.repository.all()

    def get(self, id: int):
        return self.repository.get(id)

    def update_balance(self, id: int, value_to_be_appended):
        self.repository.update_balance(id, value_to_be_appended)


class IRepository(ABC):

    @abstractmethod
    def insert(self, id, account):
        pass

    @abstractmethod
    def get(self, id):
        pass

    @abstractmethod
    def all(self):
        pass

    @abstractmethod
    def update_balance(self, account_id, value_to_be_appended):
        pass




from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.orm import DeclarativeBase
from sqlalchemy.orm import Mapped
from sqlalchemy.orm import mapped_column

class Base(DeclarativeBase):
        pass

class AccountAlchemy(Base):
    __tablename__ = "account"

    id: Mapped[int] = mapped_column(primary_key=True)
    name: Mapped[str]
    balance: Mapped[int]

    @classmethod
    def from_model(account: Account):
        return AccountAlchemy(account.name, account.balance)
    
    def to_model(self):
        return Account(self.id, self.name, self.balance)
        
class SQLAlchemyRepository(IRepository):

    def __init__(self):
        self.db: SQLAlchemy = config.db

    def insert(self, account: Account):
        self.db.session.add(AccountAlchemy.from_model(account))
        self.db.session.commit()

    def get(self, id):
        return AccountAlchemy.to_model(self.db.session.get(AccountAlchemy, id))

    def all(self):
        return [AccountAlchemy.from_model(x) for x in AccountAlchemy.query.all()]

    def update_balance(self, account_id, value_to_be_appended):
        account = self.db.session.get(AccountAlchemy, account_id)
        account.balance += value_to_be_appended
        self.db.session.commit()