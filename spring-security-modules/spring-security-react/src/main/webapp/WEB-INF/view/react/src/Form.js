import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Input from './Input'

class Form extends Component {

  constructor(props){
    super(props)
    if(props.error){
      this.state = { failure: 'wrong username or password!', errcount: 0 }
    }else{
      this.state = { errcount: 0 }
    }
  }

  handleSubmit = (event) => {
    event.preventDefault()
    if(!this.state.errcount) {
      const data = new FormData(this.form)
      fetch(this.form.action, {
        method: this.form.method,
        body: new URLSearchParams(data)
      }).then(v => {
        if(v.redirected) window.location = v.url
      })
      .catch(e => console.warn(e))
    }
  }

  handleError = (field, errmsg) => {
    if(!field) return

    if(errmsg){
      this.setState((prevState) => ({
        failure: '',
        errcount: prevState.errcount + 1, 
        errmsgs: {...prevState.errmsgs, [field]: errmsg}
      }))
    } else {
      this.setState((prevState) => ({
        failure: '',
        errcount: prevState.errcount===1? 0 : prevState.errcount-1,
        errmsgs: {...prevState.errmsgs, [field]: ''}
      }))
    }
  }

  renderError = () => {
    if(this.state.errcount || this.state.failure) {
      const errmsg = this.state.failure || Object.values(this.state.errmsgs).find(v=>v)
      console.log(`error: ${errmsg}`)
      return <div className="error">{errmsg}</div>
    }
  }

  render() {
    const inputs = this.props.inputs.map(
      ({name, placeholder, type, value, className}, index) => (
        <Input key={index} name={name} placeholder={placeholder} type={type} value={value}
          className={type==='submit'? className : ''} handleError={this.handleError} />
      )
    )
    const errors = this.renderError()
    return (
        <form {...this.props} onSubmit={this.handleSubmit} ref={fm => {this.form=fm}} >
          {inputs}
          {errors}
        </form>
    )
  }
}

Form.propTypes = {
  name: PropTypes.string,
  action: PropTypes.string,
  method: PropTypes.string,
  inputs: PropTypes.array,
  error: PropTypes.string
}

export default Form