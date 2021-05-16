import React, { Component } from "react";
import "./App.css";
import Container from "@material-ui/core/Container";
import TextField from "@material-ui/core/TextField";
import { Typography, CssBaseline, CardActions } from "@material-ui/core";
import CardHeader from "@material-ui/core/CardHeader";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
// import axios from "axios";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import { Alert } from "@material-ui/lab";
class App extends Component {
  constructor(props) {
    super(props);
    // this.handleClick = this.handleClick.bind(this);
    this.state = {
      coin: 0,
      coins: true,
      code: null,
      message: "",
      amount: 0,
      responseCode: null,
      open: false,
      changes: [],
    };
  }

  // handleClick = (event) => {
  //   this.setState({ coin: event.target.innerText });
  //   // console.log("Event coin - ", event.target.innerText);
  // };

  // goBack = (event) => {
  //   this.setState({ coin: 0 });
  // };

  handleTextFieldChange(event) {
    event.preventDefault();
    this.setState({
      amount: event.target.value,
    });
  }

  calculateChange(event) {
    event.preventDefault();
    // var request = {
    //   amount: this.state.amount,
    //   coin: this.state.coin,
    // };
    // request = JSON.stringify(request);
    // axios
    //   .post(`http://localhost:8080/api/v1/vm`, {
    //     headers: {
    //       "Access-Control-Allow-Origin": "*",
    //     },
    //     request,
    //   })
    //   .then((res) => {
    //     console.log(res);
    //     console.log(res.data);
    //   });
    fetch("http://localhost:8080/api/v1/vm", {
      method: "post",
      headers: {
        Accept: "application/json, text/plain, */*",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ amount: this.state.amount }),
    })
      .then((res) => res.json())
      .then((res) => {
        try {
          // console.log(res);
          if (res.code === 0) {
            this.setState({
              open: false,
              changes: res.changes,
              coins: false,
              message: null,
            });
          } else {
            this.setState({
              open: false,
              changes: null,
              code: res.code,
              message: res.message,
            });
          }
        } catch (err) {
          console.log(err);
        }
      })
      .then((res) => console.log(res));
  }
  render() {
    const handleClickOpen = () => {
      this.setState({
        open: true,
      });
    };

    const handleClose = () => {
      this.setState({
        open: false,
      });
    };

    const coins = [5, 10, 20, 50, 100, 200];
    // console.log("message - ", this.state.message);
    return (
      <div className="App">
        <CssBaseline>
          <Dialog
            open={this.state.open}
            onClose={handleClose}
            aria-labelledby="responsive-dialog-title"
          >
            <DialogTitle id="responsive-dialog-title">
              {"Please check your data"}
            </DialogTitle>
            <DialogContent>
              <DialogContentText>
                <p>
                  Your entered amount:<strong>{this.state.amount}</strong>
                </p>
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button
                variant="outlined"
                autoFocus
                onClick={handleClose}
                color="primary"
              >
                Close
              </Button>
              <Button
                variant="outlined"
                onClick={(e) => this.calculateChange(e)}
                color="primary"
                autoFocus
              >
                Send
              </Button>
            </DialogActions>
          </Dialog>
          <Container className="container">
            <Typography variant="h4" align="center" component="h4" gutterBottom>
              💰 Coin Machine
            </Typography>

            {this.state.coins ? (
              <CardContent>
                <CardHeader title="The Following Coins are available in your machine" />
                {coins.map((item, index) => {
                  return (
                    <a key={index} className="coin silver">
                      {/* {item} */}
                      <p>{item}</p>
                    </a>
                  );
                })}
              </CardContent>
            ) : null}
            {this.state.message ? (
              <Alert severity="error">{this.state.message}</Alert>
            ) : null}

            <CardContent>
              <CardHeader title="Please enter the amount" />
              <TextField
                onChange={(e) => this.handleTextFieldChange(e)}
                type="number"
                id="filled-basic"
                label="Enter your amount"
                variant="filled"
              />{" "}
              <br></br>{" "}
              <CardActions>
                <Button
                  className="submit"
                  variant="contained"
                  color="primary"
                  onClick={handleClickOpen}
                >
                  Submit
                </Button>
              </CardActions>
            </CardContent>
            <CardContent>
              <CardHeader />
              {this.state.changes
                ? this.state.changes.map((d, index) => (
                    <a key={index} className="coin silver">
                      <p>{d}</p>
                    </a>
                  ))
                : null}
            </CardContent>
          </Container>
        </CssBaseline>
      </div>
    );
  }
}
export default App;
