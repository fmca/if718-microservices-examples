from flask import Blueprint, request
from model import AccountController

class AccountControllerMVC:

    def __init__(self) -> None:
        self.blueprint = Blueprint("AccountControllerMVC", __name__)
        self.ctrl: AccountController = AccountController()
        self.blueprint.add_url_rule("/", "index", self.index, methods=["GET"])
        self.blueprint.add_url_rule("/form", "form", self.form, methods=["GET"])
        self.blueprint.add_url_rule("/form", "new_account", self.new_account, methods=["POST"])
        self.blueprint.add_url_rule("/<id>/enoughBalance/<value>", "has_enough_balance", self.has_enough_balance, methods=["GET"])
        self.blueprint.add_url_rule("/<id>/balance", "update_balance", self.update_balance, methods=["PUT"])

    def index(self):
        return "OK"
    
    def form(self):
        return "ok"
    
    def new_account(self):
        return "OK"
    
    def has_enough_balance(self, id, value):
        return self.ctrl.has_enough_balance(id, value)
    
    def update_balance(self, id):
        value = request.data.get('value')
        return self.ctrl.update_balance(id, value)
    