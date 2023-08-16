from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from communication import AccountControllerMVC
from model import config

# create the extension
db = SQLAlchemy()
# create the app
app = Flask(__name__)
app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///account.db"

# mvc controllers
app.register_blueprint(AccountControllerMVC().blueprint, url_prefix="/account")

# configure the SQLite database, relative to the app instance folder
# initialize the app with the extension
config.db = db
db.init_app(app)